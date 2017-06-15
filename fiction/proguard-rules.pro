
##---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature,EnclosingMethod,InnerClasses
-dontpreverify
-dontshrink
-dontoptimize
-ignorewarnings
##--------------------------注解--------------------
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }
### GSON
-keep class sun.misc.Unsafe.* { *; }
-keep class com.google.gson.stream.* { *; }
-keep class com.google.gson.examples.android.model.* { *; }
-keep class com.google.gson.* { *;}

### klog
-keep class com.socks.** {*;}

### FlexboxLayout
-keep class com.google.android.flexbox.** {*;}

### rx
-keep class io.reactivex.** {*;}

### rxJsoupNetWork
-keep class com.rxjsoupnetwork.** {*;}

## flowlayout
-keep class org.apmem.tools.layouts.**{*;}

## jsoup
-keep class org.jsoup.**{*;}

### Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

### greendao

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.greenrobot.greendao.database.**

### squareup

-keep class com.squareup.**{*;}


### jiecao

-keep class fm.jiecao.jcvideoplayer_lib.**{*;}

### xadapter

-keep class com.xadapter.**{*;}

##---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class roboguice.** { *; }
#
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class **.R$* {
*;
}
-keepclassmembers class * {
    void *(**On*Event);
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
##---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
  public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
##--------------------删掉所有log------------------------------
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** w(...);
#    public static *** v(...);
#    public static *** i(...);
#}