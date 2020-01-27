# Instastory

> Creates an instagram story like UI 

## Usage

	
### Initiate
```kotlin
    val listOfViews = listOf(
       StoryItem.Text("Hello", 5),
       StoryItem.RemoteImage(imageUrl, 5),
       StoryItem.Video(videoUrl, 5),
       StoryItem.LocalImage(R.drawable.some_drawable, 5),
       StoryItem.CustomLayout(R.layout.custom_view, 5)
    )

    Story(applicationContext, listOfViews, container, object : StoryCallback { ... } ).start
```

## Install

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
  implementation 'com.github.cesarferreira:instastory:+'
}
```

## Licence

```
Copyright 2020 Cesar Ferreira

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```