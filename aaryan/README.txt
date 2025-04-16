Book List Android Application Setup Instructions

1. Required Gradle Changes:
   Add the following dependencies to your app-level build.gradle file:

   dependencies {
       implementation 'androidx.appcompat:appcompat:1.6.1'
       implementation 'com.google.android.material:material:1.9.0'
       implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
   }

2. Android Manifest Changes:
   Add internet permission to AndroidManifest.xml:
   <uses-permission android:name="android.permission.INTERNET" />

3. API Setup:
   - Go to https://designer.mocky.io
   - Create a new mock API endpoint
   - Configure it to return XML response in the following format:
     <books>
       <book>
         <title>Book Title</title>
         <year>2025</year>
       </book>
       ...
     </books>
   - Replace the URL in MainActivity.java with your mocky.io endpoint URL

4. Implementation Notes:
   - The app will automatically fetch and display book titles
   - Books published in 2025 will trigger a custom broadcast
   - The broadcast is received and displayed in the same TextView
   - Network operations are performed on a background thread
   - UI updates are handled on the main thread

5. Testing:
   - Ensure you have an active internet connection
   - Run the app to see the book list
   - If any book has year 2025, you'll see a special alert message 