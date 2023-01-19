
# WSGithubAppKotlin简介

🔥 🔥 🔥 WSGithubAppKotlin项目，使用自主开发  [Cams](https://github.com/WeiShuaiDev/Cams)包，内部提供组件化工具，支持每个业务模块切换到指定`MVP`、`MVVM`、架构(`framework`)，同时提供每个架构模板，在创建项目可以选择对应模板，自动生成对应架构模板代码，提高开发效率。开发该项目主要目的尝试在实际开发中使用[Cams](https://github.com/WeiShuaiDev/Cams)库，是否会出现不兼容问题，通过这次项目，也发现一些问题，不过已经进行优化处理。

![project_structure](https://github.com/WeiShuaiDev/WSGithubAppKotlin/blob/main/screenshots/project_structure.png?raw=true)

## 一、项目简介

​	`WSGithubAppKotlin`项目所有接口数据来源于`Github`平台，App中主要分为3个模块:推荐、动态、我的、登录。项目使用大量Dagger技术，从基础层，到架构层，再到应用层使用Dagger继续注入，同时提供一些配置入口，方便每个层级配置。项目拿来锻炼学习Dagger技术已足够，基本涵盖所有Dagger功能。

### 二、项目结构

├── cams    基础库

├── cams-mvp  mvp架构

├── cams-mvp-template  mvp架构模板

├── cams-mvvm  mvvm架构

├── cams-mvvm-template  mvvm架构模板

├── github-mvvm   github app

├── gradle

├── build.gradle

├── config.gradle 依赖包

### 三、依赖明细

```groovy
 dependencies = [
            /**
             * ---------------------------------------------Android X-------------------------------
             */
            "appcompat"                   : "androidx.appcompat:appcompat:${version["androidXSdkVersion"]}",
            "constraintlayout"            : "androidx.constraintlayout:constraintlayout:2.0.1",

            //recyclerview
            "recyclerview"                : "androidx.recyclerview:recyclerview:${version["androidXSdkVersion"]}",

            "legacy-support-v4"           : "androidx.legacy:legacy-support-v4:1.0.0",
            "annotation"                  : "androidx.annotation:annotation:1.0.0",

            /**
             * ---------------------------------------------Kotlin----------------------------------
             */
            "kotlin-stdlib-jdk7"          : "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${version["kotlinVersion"]}",
            "kotlin-reflect"              : "org.jetbrains.kotlin:kotlin-reflect:${version["kotlinVersion"]}",
            "core-ktx"                    : "androidx.core:core-ktx:1.2.0",
            "anko-common"                 : "org.jetbrains.anko:anko-common:${version["ankoVersion"]}",

            /**
             * ----------------------------------------------JetPack--------------------------------
             */
            //Navigation
            "navigation-fragment-ktx"     : "androidx.navigation:navigation-fragment-ktx:${version["navigationVersion"]}",
            "navigation-ui-ktx"           : "androidx.navigation:navigation-ui-ktx:${version["navigationVersion"]}",
            // liveData
            "lifecycle-livedata-ktx"      : "androidx.lifecycle:lifecycle-livedata-ktx:${version["lifecycleVersion"]}",
            // viewModel
            "lifecycle-viewmodel-ktx"     : "androidx.lifecycle:lifecycle-viewmodel-ktx:${version["lifecycleVersion"]}",
            "lifecycle-extensions"        : "androidx.lifecycle:lifecycle-extensions:${version["lifecycleVersion"]}",
            "fragment-ktx"                : "androidx.fragment:fragment-ktx:${version["fragmentVersion"]}",
            // room
            "room-runtime"                : "androidx.room:room-runtime:${version["roomVersion"]}",
            "room-ktx"                    : "androidx.room:room-ktx:${version["roomVersion"]}",
            "room-compiler"               : "androidx.room:room-compiler:${version["roomVersion"]}",
            "room-testing"                : "androidx.room:room-testing:${version["roomVersion"]}",
            // paging
            "paging-runtime"              : "androidx.paging:paging-runtime:${version["pagingVersion"]}",
            //work
            "work-runtime-ktx"            : "androidx.work:work-runtime-ktx:${version["workVersion"]}",

            /**
             * ---------------------------------------------Http------------------------------------
             */
            //OkHttp
            "okhttp"                      : "com.squareup.okhttp3:okhttp:${version["okhttpVersion"]}",
            "okhttp-logging-interceptor"  : "com.squareup.okhttp3:logging-interceptor:${version["okhttpVersion"]}",
            "okhttp-urlconnection"        : "com.squareup.okhttp:okhttp-urlconnection:2.0.0",
            //Retrofit
            "retrofit"                    : "com.squareup.retrofit2:retrofit:${version["retrofitVersion"]}",
            "retrofit-converter-gson"     : "com.squareup.retrofit2:converter-gson:${version["retrofitVersion"]}",
            "retrofit-adapter-rxjava"     : "com.squareup.retrofit2:adapter-rxjava:${version["retrofitVersion"]}",
            "retrofit-adapter-rxjava2"    : "com.squareup.retrofit2:adapter-rxjava2:${version["retrofitVersion"]}",
            "retrofit-adapter-rxjava3"    : "com.squareup.retrofit2:adapter-rxjava3:${version["retrofitVersion"]}",
            "retrofit-converter-scalars"  : "com.squareup.retrofit2:converter-scalars:${version["retrofitVersion"]}",
            "retrofit-converter-simplexml": "com.squareup.retrofit2:converter-simplexml:${version["retrofitVersion"]}",

            //Glide
            "glide"                       : "com.github.bumptech.glide:glide:${version["glideVersion"]}",
            "glide-transformations"       : "jp.wasabeef:glide-transformations:${version["glide_trasnVersion"]}",


            /**
             * ---------------------------------------------Rx1-------------------------------------
             */
            "rxkotlin"                    : "io.reactivex:rxkotlin:1.0.0",
            "rxandroid"                   : "io.reactivex:rxandroid:1.2.1",
            "rxlifecycle"                 : "com.trello:rxlifecycle:${version["rxlifecycleSdkVersion"]}",
            "rxlifecycle-kotlin"          : "com.trello.rxlifecycle2:rxlifecycle-kotlin:${version["rxlifecycleSdkVersion"]}",
            "rxjava"                      : "io.reactivex:rxjava:1.3.8",
            "rxcache"                     : "com.github.VictorAlbertos.RxCache:runtime:1.7.0-1.x",
            "rxcache-jolyglot-gson"       : "com.github.VictorAlbertos.Jolyglot:gson:0.0.4",
            "rxpermissions"               : "com.tbruyelle.rxpermissions:rxpermissions:0.9.4@aar",

            /**
             * ---------------------------------------------Rx2-------------------------------------
             */
            "rxkotlin2"                   : "io.reactivex.rxjava2:rxkotlin:2.4.0",
            "rxandroid2"                  : "io.reactivex.rxjava2:rxandroid:2.1.1",
            "rxlifecycle2"                : "com.trello.rxlifecycle2:rxlifecycle:${version["rxlifecycle2SdkVersion"]}",
            "rxlifecycle2-kotlin"         : "com.trello.rxlifecycle2:rxlifecycle-kotlin:${version["rxlifecycle2SdkVersion"]}",
            "rxlifecycle2-components"     : "com.trello.rxlifecycle2:rxlifecycle-components:${version["rxlifecycle2SdkVersion"]}",
            "rxjava2"                     : "io.reactivex.rxjava2:rxjava:2.2.19",
            "rxcache2"                    : "com.github.VictorAlbertos.RxCache:runtime:1.8.3-2.x",
            "rxpermissions2"              : "com.github.tbruyelle:rxpermissions:0.10.2",

            /**
             * ---------------------------------------------Rx3-------------------------------------
             */
            "rxkotlin3"                   : "io.reactivex.rxjava3:rxkotlin:3.0.0",
            "rxandroid3"                  : "io.reactivex.rxjava3:rxandroid:3.0.0",
            "rxlifecycle3"                : "com.trello.rxlifecycle4:rxlifecycle:${version["rxlifecycle3SdkVersion"]}",
            "rxlifecycle3-kotlin"         : "com.trello.rxlifecycle4:rxlifecycle-kotlin:${version["rxlifecycle3SdkVersion"]}",
            "rxlifecycle3-components"     : "com.trello.rxlifecycle4:rxlifecycle-components:${version["rxlifecycle3SdkVersion"]}",
            "rxjava3"                     : "io.reactivex.rxjava3:rxjava:3.0.4",


            /**
             * ---------------------------------------------Tools-----------------------------------
             */
            "dagger2"                     : "com.google.dagger:dagger:${version["dagger2SdkVersion"]}",
            "dagger2-android"             : "com.google.dagger:dagger-android:${version["dagger2SdkVersion"]}",
            "dagger2-android-support"     : "com.google.dagger:dagger-android-support:${version["dagger2SdkVersion"]}",
            "dagger2-compiler"            : "com.google.dagger:dagger-compiler:${version["dagger2SdkVersion"]}",
            "dagger2-android-processor"   : "com.google.dagger:dagger-android-processor:${version["dagger2SdkVersion"]}",
            "arouter-api"                 : "com.alibaba:arouter-api:${version["arouterApiVersion"]}",
            "arouter-compiler"            : "com.alibaba:arouter-compiler:${version["arouterCompilerVersion"]}",
            "arouter-register"            : "com.alibaba:arouter-register:${version["arouterRegister"]}",


            "eventbus"                    : "org.greenrobot:eventbus:${version["eventbusVersion"]}",
            "rxbus"                       : "com.eightbitlab:rxbus:${version["rxbusVersion"]}",
            "otto"                        : "com.squareup:otto:${version["ottoVersion"]}",
            "multidex"                    : "com.android.support:multidex:${version["multidexVersion"]}",
            "fastjson"                    : "com.alibaba:fastjson:${version["fastjsonVersion"]}",
            "gson"                        : "com.google.code.gson:gson:${version["gsonVersion"]}",
            "easypermissions"             : "pub.devrel:easypermissions:${version["easypermissionVersion"]}",

            /**
             * ---------------------------------------------View------------------------------------
             */

            "material"                    : "com.google.android.material:material:1.0.0",
            //StateView
            "stateview"                   : "com.github.nukc.stateview:kotlin:${version["stateviewVersion"]}",
            "stateview_animation"         : "com.github.nukc.stateview:animations:1.0.2",
            //ParallaxBackLayout
            "parallaxbacklayout"          : "com.github.anzewei:parallaxbacklayout:${version["parallaxbacklayoutVersion"]}",
            //Animation
            "avi"                         : "com.wang.avi:library:${version["aviVersion"]}",

            /**
             * ---------------------------------------------Test------------------------------------
             */
            "junit"                       : "junit:junit:4.12",
            "test-junit"                  : 'androidx.test.ext:junit:1.1.1',
            "androidJUnitRunner"          : "androidx.test.runner.AndroidJUnitRunner",
            "runner"                      : "com.android.support.test:runner:1.0.1",
            "espresso-core"               : "com.android.support.test.espresso:espresso-core:${version["espressoSdkVersion"]}",
            "espresso-contrib"            : "com.android.support.test.espresso:espresso-contrib:${version["espressoSdkVersion"]}",
            "espresso-intents"            : "com.android.support.test.espresso:espresso-intents:${version["espressoSdkVersion"]}",
            "mockito-core"                : "org.mockito:mockito-core:1.+",
            "timber"                      : "com.jakewharton.timber:timber:4.7.1",
            "logger"                      : "com.orhanobut:logger:2.2.0",
            "canary-debug"                : "com.squareup.leakcanary:leakcanary-android:${version["canarySdkVersion"]}",
            "canary-release"              : "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3",
            "umeng-analytics"             : "com.umeng.analytics:analytics:6.0.1"

    ]
```

