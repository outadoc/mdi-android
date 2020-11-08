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
    id("java-library")
    id("maven-publish")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    explicitApi()
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("commonLibMaven") {
                groupId = "fr.outadoc.mdi"
                artifactId = "mdi-common"
                version = Version.getMdiVersionName(project.rootDir.toPath())

                from(components["java"])
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                setUrl("https://maven.pkg.github.com/outadoc/mdi-android")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
