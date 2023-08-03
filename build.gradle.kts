plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.0.0").apply(false)
    id("com.android.library").version("8.0.0").apply(false)
    kotlin("android").version("1.7.10").apply(false)
   // kotlin("multiplatform").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)

   // id 'org.jetbrains.kotlin.jvm' version "1.4.10"
//id ("org.jetbrains.kotlin.android").version("1.7.10").apply(false)
  //  id("org.jetbrains.kotlin.android").version("1.7.10").apply(false)
   // id("com.android.tools.build").version("7.2.2").apply(false)
    id("com.squareup.sqldelight").version("1.5.3").apply(false)
    id("com.google.dagger.hilt.android").version("2.42").apply(false)

   // id("com.android.application") version "8.0.0" apply false
   // id("com.android.library") version "8.0.0" apply false
   // id("org.jetbrains.kotlin.android") version "1.5.31" apply false
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
