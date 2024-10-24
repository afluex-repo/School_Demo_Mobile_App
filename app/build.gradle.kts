plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "affluex.school.solutions"
    compileSdk = 34

    defaultConfig {
        applicationId = "affluex.school.solutions"
        minSdk = 24
        targetSdk = 34
        versionCode = 6
        versionName = "1.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"https://school.afluex.com/\"")
        }

        release {
//            buildConfigField "String", "BASE_URL", "\"http://demo1.afluex.com/webapi/\""
            buildConfigField("String", "BASE_URL", "\"https://school.afluex.com/\"")

            buildFeatures {
                viewBinding = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.3"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("io.github.chaosleung:pinview:1.4.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation("de.hdodenhof:circleimageview:3.1.0")
        implementation("com.karumi:dexter:5.0.0")
        implementation("com.google.android.gms:play-services-location:16.0.0")
        implementation ("com.github.p32929:AndroidEasySQL-Library:1.4.1")
        implementation ("com.squareup.picasso:picasso:2.71828")
        implementation ("com.github.bumptech.glide:glide:4.16.0")
      //  implementation ("com.github.smarteist:autoimageslider:1.4.0-appcompat")
        implementation ("com.github.smarteist:autoimageslider:1.3.9")

        //Retrofit APIs
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-scalars:2.6.1")
        implementation ("com.squareup.retrofit2:converter-gson:2.6.1")
        implementation ("com.squareup.okhttp3:logging-interceptor:3.9.0")
        implementation ("io.github.inflationx:viewpump:2.0.3")
        implementation ("io.github.inflationx:calligraphy3:3.1.1")
        implementation ("com.github.dhaval2404:imagepicker:2.1")
    }
}

dependencies {
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


}
