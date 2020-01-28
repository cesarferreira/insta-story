# Insta-story [![Build Status](https://travis-ci.org/cesarferreira/insta-story.svg?branch=master)](https://travis-ci.org/cesarferreira/insta-story) [![](https://jitpack.io/v/cesarferreira/insta-story.svg)](https://jitpack.io/#cesarferreira/insta-story)


> Creates an instagram story like UI 

## Usage

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

