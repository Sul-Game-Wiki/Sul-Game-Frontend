import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("kotlin-android-extensions")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "info.sul_game"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }

    defaultConfig {
        applicationId = "info.sul_game"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "my-new-key-alias"
            keyPassword = "Tjtocks178@"
            storeFile = file("release-key.jks")
            storePassword = "Tjtocks178@"
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // 인증정보추가
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
//            options.jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // Retrofit with Scalar Converter
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.2")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("com.nambimobile.widgets:expandable-fab:1.2.1")
    implementation("org.jsoup:jsoup:1.18.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("me.relex:circleindicator:2.1.6")
    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging:24.0.1")
//    classpath("com.google.gms:google-services:4.4.2")
//    apply plugin:("com.google.gms.google-services")

}