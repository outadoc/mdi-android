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

package fr.outadoc.mdi

import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.common.MdiStringRef
import fr.outadoc.mdi.common.MdiMapperLocator
import fr.outadoc.mdi.common.NoSuchIconException
import java.util.Locale

fun @MdiStringRef String.toIconOrNull(): MdiFontIcon? {
    val cleanup = takeLastWhile { it != ':' }.toLowerCase(Locale.US)
    return MdiMapperLocator.instance?.getIcon(cleanup)
}

fun @MdiStringRef String.toIcon() =
    toIconOrNull() ?: throw NoSuchIconException(this)
