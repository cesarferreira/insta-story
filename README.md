# Insta-story [![](https://jitpack.io/v/cesarferreira/insta-story.svg)](https://jitpack.io/#cesarferreira/insta-story)

> Creates an instagram story like UI 

## Usage

```kotlin
val listOfViews = listOf(
    StoryItem.Text(text = "Hello", durationInSeconds = 5),
    StoryItem.RemoteImage(imageUrl = imageUrl, durationInSeconds = 5),
    StoryItem.Video(videoUrl = videoUrl),
    StoryItem.LocalImage(R.drawable.some_image, durationInSeconds = 5),
    StoryItem.CustomLayout(R.layout.custom_view, durationInSeconds = 5)
)

InstaStory(context, listOfViews, container, object : StoryCallback { ... } ).start()
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

