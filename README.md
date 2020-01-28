# Insta-story [![Build Status](https://travis-ci.org/cesarferreira/insta-story.svg?branch=master)](https://travis-ci.org/cesarferreira/insta-story) [ ![Download](https://api.bintray.com/packages/cesarferreira/maven/insta-story/images/download.svg) ](https://bintray.com/cesarferreira/maven/insta-story/_latestVersion)

> Creates an instagram story like UI 

## Usage

	
### Initiate
```kotlin
    val listOfViews = listOf(
       StoryItem.Text("Hello", 5),
       StoryItem.RemoteImage(imageUrl, 5),
       StoryItem.Video(videoUrl),
       StoryItem.LocalImage(R.drawable.some_drawable, 5),
       StoryItem.CustomLayout(R.layout.custom_view, 5)
    )

    Story(context, listOfViews, container, object : StoryCallback { ... } ).start()
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
  implementation 'com.github.cesarferreira:insta-story:+'
}
```

---------------------


Made with ♥ by [Cesar Ferreira](http://cesarferreira.com)


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