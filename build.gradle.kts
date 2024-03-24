import org.spongepowered.asm.gradle.plugins.MixinExtension
import org.spongepowered.asm.gradle.plugins.struct.DynamicProperties
import java.text.SimpleDateFormat
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
        classpath("org.spongepowered:mixingradle:0.7.+")
    }
}

apply(plugin = "kotlin")
apply(plugin = "org.spongepowered.mixin")

plugins {
    eclipse
    `maven-publish`
    id("net.minecraftforge.gradle") version "5.1.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

version = findProperty("version")!! as String
group = findProperty("group")!! as String

val modID = findProperty("modID")!! as String
val vendor = findProperty("vendor")!! as String
val curiosVersion = findProperty("curiosVersion")!! as String

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

println(
    "Java: " + System.getProperty("java.version") + " JVM: " + System.getProperty("java.vm.version") + "(" + System.getProperty(
        "java.vendor"
    ) + ") Arch: " + System.getProperty("os.arch")
)

minecraft {
    mappings("official", "1.19.2")
    mappings("parchment", "2022.08.14-1.19.2")
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs.all {
        mods {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", modID)
            property("terminal.jline", "true")

            mods {
                create(modID) {
                    source(sourceSets.main.get())
                }
            }
        }
    }

    runs.run {
        create("client") {
            property("log4j.configurationFile", "log4j2.xml")

            // for hotswap https://forge.gemwire.uk/wiki/Hotswap
            jvmArg("-XX:+AllowEnhancedClassRedefinition")
            jvmArg("-XX:+IgnoreUnrecognizedVMOptions")

            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${projectDir}/build/createSrgToMcp/output.srg")

            args(
                "--username", "Player",
            )
        }

        create("server") {}

        create("gameTestServer") {}

        create("data") {
            workingDirectory(project.file("run"))
            args(
                "--mod",
                modID,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources")
            )
        }

    }
}

configurations {
    minecraftLibrary {
        exclude("org.jetbrains", "annotations")
    }
}

val Project.mixin: MixinExtension
    get() = extensions.getByType()

mixin.run {
    add(sourceSets.main.get(), "${modID}.mixins.refmap.json")
    config("${modID}.mixins.json")
    val debug = this.debug as DynamicProperties
    debug.setProperty("verbose", true)
    debug.setProperty("export", true)
    setDebug(debug)
}

configurations {
    minecraftLibrary {
        exclude("org.jetbrains", "annotations")
    }
}

repositories {
    mavenCentral()
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven {
        name = "Illusive Soulworks"
        url = uri("https://maven.theillusivec4.top/")
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:1.19.2-43.3.0")
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")

    implementation("thedarkcolour:kotlinforforge:3.12.0")

    compileOnly(fg.deobf("top.theillusivec4.curios:curios-forge:${curiosVersion}:api"))
    runtimeOnly(fg.deobf("top.theillusivec4.curios:curios-forge:${curiosVersion}"))
}

sourceSets.main.configure { resources.srcDirs("src/generated/resources/") }

tasks.withType<Jar> {
    archiveBaseName.set(modID)
    manifest {
        val map = HashMap<String, String>()
        map["Specification-Title"] = modID
        map["Specification-Vendor"] = vendor
        map["Specification-Version"] = "1"
        map["Implementation-Title"] = project.name
        map["Implementation-Version"] = project.version.toString()
        map["Implementation-Vendor"] = vendor
        map["Implementation-Timestamp"] = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        attributes(map)
    }
    finalizedBy("reobfJar")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/mcmodsrepo")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}
