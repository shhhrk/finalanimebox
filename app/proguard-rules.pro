# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retrofit, Gson, API
-keep class com.example.animebox.data.api.** { *; }
-keep interface com.example.animebox.data.api.** { *; }
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Room
-keep class androidx.room.** { *; }
-keep class com.example.animebox.data.** { *; }
-keep class com.example.animebox.domain.** { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-keepattributes RuntimeVisibleAnnotations