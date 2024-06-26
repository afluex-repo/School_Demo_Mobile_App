pluginManagement {
    repositories {
        google()
        jcenter()
        mavenCentral()


        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven (url= "https://mvnrepository.com")
        maven (
            url= "https://jcenter.bintray.com"
            )
    }
}


rootProject.name = "School Solutions"
include(":app")
