# Walmart-Assignment
This app displays a list of countries

## Technical Summary
The app uses Android Jetpack and Retrofit to fetch and persist a list of countries.
It follows an MVVM separation of concerns.  It follows Clean Architecture
guidelines but omits a usecase as it would be excessive boilerplate for this
simple feature.  It uses the database as a source of truth for consistency and simplicity.

## Building and installing the app
The app requires at least Android 7.0.  Build and install the project using Android Studio.
Alternatively use the gradle wrapper on a command line.  At the project root execute ./gradlew
installDebug with a running device or emulator.

## Automated Testing
Unit tests for the viewmodel and repository are available.  Run and view results in Android Studio.
Or use the gradle wrapper on a command line by running ./gradlew testDebug at the project root.
Open PROJECT_ROOT/app/build/reports/tests/testDebugUnitTest/index.html to review a breakdown of
test results.