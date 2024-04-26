# ExamGenerator

## How to use

```bash
gradle run --args="<template.html> <studentNumbers.txt> <encryptKey>"
```
or

```bash
gradle jar
```
```bash
java -jar build/libs/app.jar <template.html> <studentNumbers.txt> <encryptKey>
```

## Template
The template must have a `<div class="page">` that contain a `{{anocode}}` and a `{{placeNumber}}` that will be replaced by the program by the corresponding data.

## studentNumbers

The file must have all students numbers line by line

## encryptKey

A numeric key ex: 123456

## Ouput
The program will ouput an exam.html printable (should tweak settings to get the best result) and a anoCode.csv that contains the students numbers and the corresponding anonym code.#

## Exemple files
I made exemple files: `app/src/numEtu.txt` and `app/src/template.html`