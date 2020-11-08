# mdi-android
This library provides an Android wrapper around the [Material Design Icons](https://materialdesignicons.com/)
icon library, for convenient use in Android applications.

THE ICONS WRAPPED BY THIS LIBRARY ARE PROVIDED AS-IS AND COPYRIGHTED TO A THIRD-PARTY. Please see the
License section for details.

## Setup
This library is hosted on Github Packages. In your projec's root `build.gradle.kts` file, add the Github Packages repository:

```kt
allprojects {
    repositories {
        google()
        jcenter()

        // [...]

        maven(url = "https://maven.pkg.github.com/outadoc/mdi-android") {
            credentials {
                username = "token"
                password = "TOKEN-WITH-READ-PACKAGES-ROLE-HERE"
            }
        }
    }
}
```

You will need to [generate a Github Access Token](https://github.com/settings/tokens/new). Give it the `read:packages` scope.

In your module's `build.gradle.kts`, add the dependency:

```kt
dependencies {
    // [...]
    implementation("fr.outadoc.mdi:mdi-android:+")
}
```

## Usage
You can use a custom view to display your icons:

```xml
<fr.outadoc.mdi.MdiFontIconView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="32sp"
    app:icon="toaster-oven" />
```

You can also use a standard appcompat `TextView`. You just have to set the `TextAppearance.MaterialDesignIcons`
text appearance on it.

```xml
<TextView
    android:id="@+id/textView_fontIcon_example"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textAppearance="@style/TextAppearance.MaterialDesignIcons"
    android:textSize="32sp" />
```

In code, setup the library and convert the icon's reference into the right object.

```kt
// Create an instance of the Android icon mapper with a Context,
// and set it on the MdiMapperLocator. Do this in your Application class or similar.
MdiMapperLocator.instance = AndroidMdiMapper(applicationContext)

// Convert an icon's reference to a proper font icon instance
val icon1: MdiFontIcon  = "toaster-oven".toIcon()
val icon2: MdiFontIcon? = "toaster-oven".toIconOrNull()

// Set the icon on the TextView
textView_fontIcon_example.setText(icon1.unicodePoint)

// Or using the ktx
textView_fontIcon_example.setIcon(icon1)
```

## License
The icons provided by this library are made and maintained by the [Pictogrammers](http://pictogrammers.com/)
icon group. See more at @templarian/MaterialDesign.

The source code of this repository is available under the Apache 2.0 License.
