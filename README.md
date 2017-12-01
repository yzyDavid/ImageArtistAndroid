# ImageArtistAndroid

## migrate to OpenCV

- download openCV Android SDK: https://sourceforge.net/projects/opencvlibrary/files/opencv-android/3.3.1/opencv-3.3.1-android-sdk.zip/download
- unzip it in the root of project directory.
- documents are here: https://docs.opencv.org/2.4/doc/tutorials/introduction/android_binary_package/dev_with_OCV_on_Android.html
- in FIle/Project Structure:
  - new Eclipse ADT module opencv.
  - for app: add module dependency loaded.
- copy OpenCV-android-sdk/sdk/native/libs to app/src/main/jniLibs