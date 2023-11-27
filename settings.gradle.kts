pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

val keyProps = java.util.Properties().apply{
    file("gradle.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()

        // jitpack for epublib
        maven(url=uri("https://jitpack.io"))

        // Epublib
        maven(url="https://github.com/psiegman/mvn-repo/raw/master/releases")

        // MQTT
        maven(url = "https://repo.eclipse.org/content/repositories/paho-snapshots/")
        }

}

rootProject.name = "KittyReader"
include(":app")


 