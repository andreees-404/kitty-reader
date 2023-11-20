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
        maven(url=uri("https://jitpack.io"))
        maven(url="https://github.com/psiegman/mvn-repo/raw/master/releases")


    }

}

rootProject.name = "KittyReader"
include(":app")


 