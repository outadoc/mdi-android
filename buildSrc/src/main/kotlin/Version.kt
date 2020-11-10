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

import java.nio.file.Path

object Version {

    private const val mdiLibRevision = 'h'

    const val commonLibVersion = "1.0.0"

    const val minSdkVersion = 21
    const val targetSdkVersion = 30

    private fun getNpmPackageVersion(projectRootPath: Path): String {
        ProcessBuilder()
            .directory(projectRootPath.toFile())
            .command("./script/get-mdi-version.sh")
            .start()
            .let { process ->
                process.waitFor()
                process.inputStream
                    .bufferedReader()
                    .use { reader ->
                        return reader.readLine().trim()
                    }
            }
    }

    fun getMdiVersionName(projectRootPath: Path): String {
        return "${getNpmPackageVersion(projectRootPath)}-$mdiLibRevision"
    }

    private val alphabet = 'a'..'z'
    private val numericRevision = alphabet.indexOf(mdiLibRevision)

    fun getMdiVersionCode(projectRootPath: Path): Int {
        val str = getNpmPackageVersion(projectRootPath)
            .split(".")
            .joinToString("") {
                it.padStart(3, '0')
            }

        val revisionStr = numericRevision.toString().padStart(2, '0')
        return "${str}${revisionStr}".toInt()
    }
}