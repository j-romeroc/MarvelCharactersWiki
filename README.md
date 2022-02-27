# marvel-characters-wiki

## Overview

The following Android app lists all of the characters in the Marvel Universe with the help of the Marvel API (the https://developer.marvel.com/docs). 

The app is intended as an example of a clean mvvm architecture following good practices in Android development. It uses data binding for the UI and Koin as DI.

It consists of three Fragments with the following functionalitites:

- CharacterListFragment: List of Marvel Characters with their image, id number and name. When tapping an item, it navigates to CharacterDetailFragment.
- CharacterDetailFragment: It adds additional info for each character. When tapping the More Info button, it navigates to WebviewFragment.
- It opens a Webview with the Marvel website that provides all the info for that character.

It also has a basic implementation of Unit Testing for both the Domain and Data layers.

##Libraries used

- [Koin](https://insert-koin.io).
- Data Binding.
- Mockito for kotlin.
- Android Navigation Component.




