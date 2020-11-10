/*
 * Copyright 2020 Baptiste Candellier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
}

android {
    compileSdkVersion(Version.targetSdkVersion)
    buildToolsVersion("30.0.1")

    defaultConfig {
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)

        versionCode(Version.getMdiVersionCode(project.rootDir.toPath()))
        versionName(Version.getMdiVersionName(project.rootDir.toPath()))

        buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kotlin {
    explicitApi()
}

dependencies {
    api(project(":lib-mdi-common"))
    implementation("androidx.appcompat:appcompat:1.3.0-alpha02")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("androidLibMaven") {
                groupId = Publishing.groupId
                artifactId = "mdi-android"
                version = android.defaultConfig.versionName

                from(components["release"])
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                setUrl(Publishing.repoUrl)
                credentials {
                    username = Publishing.repoUsername
                    password = Publishing.repoPassword
                }
            }
        }
    }
}

val scriptDirectory = "${rootProject.projectDir}/script"

val npmFontDirectory = "${rootProject.projectDir}/node_modules/@mdi/font"
val minCssFilePath = "$npmFontDirectory/css/materialdesignicons.min.css"
val ttfFilePath = "$npmFontDirectory/fonts/materialdesignicons-webfont.ttf"

val androidFontDirectory = "$projectDir/src/main/res/font"
val androidAssetsDirectory = "$projectDir/src/main/assets"
val androidMappingFilePath = "$androidAssetsDirectory/mdi_map.txt"

tasks.register<Exec>("mdiNpmInstall") {
    doFirst {
        println("installing npm package...")
    }

    workingDir(rootProject.projectDir)
    commandLine("npm", "install")
}

tasks.register<Copy>("mdiPackageCopy") {
    dependsOn("mdiNpmInstall")
    doFirst {
        println("copying font files...")
    }

    mkdir(androidFontDirectory)
    from(file(ttfFilePath)) {
        rename("(.+)-(.+)", "$1_$2")
    }
    into(file(androidFontDirectory))
}

tasks.register<Exec>("mdiGenerateFontMap") {
    dependsOn("mdiPackageCopy")
    doFirst {
        println("generating font map...")
    }

    workingDir(scriptDirectory)
    mkdir(androidAssetsDirectory)
    commandLine(
        "sh", "-c",
        "./generate-font-map.py '$minCssFilePath' >'$androidMappingFilePath'"
    )
}

tasks.register("mdiRetrieveFont") {
    dependsOn("mdiGenerateFontMap")
}

afterEvaluate {
    android.libraryVariants.forEach { variant ->
        variant.preBuildProvider.configure {
            dependsOn("mdiRetrieveFont")
        }
    }
}