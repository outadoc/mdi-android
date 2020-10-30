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

import java.nio.file.Paths

object Version {

    private const val REVISION = 'a'

    private val npmPackageVersion: String by lazy {
        ProcessBuilder()
            .directory(Paths.get("").toAbsolutePath().toFile())
            .command("./script/get-mdi-version.sh")
            .start()
            .let { process ->
                process.waitFor()
                process.inputStream
                    .bufferedReader()
                    .use { reader ->
                        return@lazy reader.readLine().trim()
                    }
            }
    }

    val mdiVersionName: String = "$npmPackageVersion-$REVISION"

    private val alphabet = 'a'..'z'
    private val numericRevision = alphabet.indexOf(REVISION)

    val mdiVersionCode: Int
        get() {
            val str = npmPackageVersion
                .split(".")
                .joinToString("") {
                    it.padStart(3, '0')
                }

            return "${str}${numericRevision}".toInt()
        }
}