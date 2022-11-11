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
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Version.targetSdkVersion)

    defaultConfig {
        applicationId = "fr.outadoc.mdi.sample"

        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)

        versionCode = Version.getMdiVersionCode(project.rootDir.toPath())
        versionName = Version.getMdiVersionName(project.rootDir.toPath())
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":lib-mdi-android"))

    implementation("org.uniflow-kt:uniflow-android:1.0.10")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.browser:browser:1.3.0")

    implementation("com.google.android.material:material:1.4.0")
    implementation("me.zhanghai.android.fastscroll:library:1.1.7")
}