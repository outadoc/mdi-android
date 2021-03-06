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

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat


public class MdiFontIconView : AppCompatTextView {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
        android.R.attr.textViewStyle
    ) {
        initWithAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initWithAttributes(context, attrs)
    }

    init {
        typeface = ResourcesCompat.getFont(context, R.font.materialdesignicons_webfont)
    }

    private fun initWithAttributes(context: Context, attrs: AttributeSet?) {
        attrs ?: return
        context.obtainStyledAttributes(attrs, R.styleable.MdiFontIconView).let { value ->
            if (value.hasValue(R.styleable.MdiFontIconView_iconName)) {
                value.getString(R.styleable.MdiFontIconView_iconName)?.let { iconStr ->
                    setIcon(iconStr.toIcon())
                }
            }
        }
    }
}