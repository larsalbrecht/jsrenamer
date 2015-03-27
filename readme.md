A simple but mighty tool to rename files

You have a bunch of files you want to rename? Something like video-/audio-/imagefiles?

# Features #
  * You can rename the file with file substrings ([name, 0, 10] - take the name from 0 to 10)
  * You can rename the file with folder substrings ([folder, 0, 10] - take the name from 0 to 10)
  * You can rename the file with counters ([counter, 1, 1, 3] - start with 1, add 1 for each and format it with 3 numbers (001, 002, 003,[...]))
  * You can rename the file with a date ([date, yyyy-MM-d] - 2014-04-13 (this is the default, use [SimpleDateFormat](http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html) to create a pattern))
  * You can rename the file with the file-extension ([extension])

  * You can short write the tags with [[n](tags.md)], [[f](tags.md)], [[c](tags.md)], [[d](tags.md)], [[e](tags.md)]
  * You can not only use the first parent folder, you can use the [[fN](tags.md)] folder

# Examples #
## Series ##
```
Example.S01E01.My.Title.avi
Example.S01E02.My.Title.avi
Example.S01E03.My.Title.avi
```

## Series (2) ##
```
Example.S01E01.My.Title.avi
Example.S01E02.My.Title.avi
Example.S01E03.My.Title.avi
Example.S02E01.My.Title.avi
Example.S02E02.My.Title.avi
Example.S02E03.My.Title.avi
```

## Series in Folder ##
```
+ Example - S01E01 - My Title
     Example.S01E01.My.Title.avi
+ Example - S01E02 - My Title
     Example.S01E02.My.Title.avi
+ Example - S01E03 - My Title
     Example.S01E03.My.Title.avi
```

## Series in Folder (2) ##
```
+ Example - S01E01 - My Title
     Example.S01E01.My.Title.avi
+ Example - S01E02 - My Title
     Example.S01E02.My.Title.avi
+ Example - S01E03 - My Title
     Example.S01E03.My.Title.avi
```

## Images ##
```
My.Image.0001.png
My.Image.0002.png
My.Image.0003.png
My.Image.0004.png
My.Image.0005.png
```

Than you can easily rename this with Java Simple Renamer.