# Tags #

| Short    | Long        | Option       | Description |
| -------- | ----------- |------------- | ----------- |
| [n]      | [name]      | [n, X, Y]    | This tag contains the original filename. As option you can only select the chars from X to Y (or only from X: [n, X]) |
| [f]      | [folder]    | [f, X, Y]    | This tag contains the name of the folder. As option you can only select the chars from X to Y (or only from X: [f, X]) |
| [c]      | [counter]   | [c, X, Y, Z] | This tag contains a counter. For each file in list, you get a number. Default it starts with zero (0). As option you can set the start (X), the step (Y) and a pre-pad (Z). |
| [d]      | [date]      | [d, X]       | This tag contains the current date. Default format is: yyyy-MM-d; As option you can set the pattern (X). You can see here (http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html) how a pattern looks like. |
| [e]      | [extension] |              | This tag contains the original file-extension (like .zip, .jpg, ...). |
| [s, X]   | [size, X]   |              | This tag formats all following text to uppercase (X = u) or lowercase (X = l). |
|-----------------------------------------------------|



## Examples ##

We have this files in list:
    Text.txt (folder: myDocuments)
    Text2.txt (folder: myDocuments)
    image-01.jpg (folder: myImages)
    image-005.jpg (folder: myImages)

If we now use the tags in the upper input, we can specify the new name of the file.

```
### [n] / [name] ###
#### Example: [n] ####
    Text.txt
    Text2.txt
    image-01.jpg
    image-005.jpg

#### Example: [n, 5] ####
    txt
    .txt
    -01.jpg
    -005.jpg

#### Example: [n, 2, 2] ####
    xt
    xt <- will not be written
    ag
    im
```

```
### [f] / [folder] ###
#### Example: [f] ####
    myDocuments
    myDocuments <- will not be written
    myImages
    myImages <- will not be written

#### Example: [f, 5] ####
    uments
    uments <- will not be written
    ges
    ges <- will not be written

#### Example: [f, 2, 2] ####
    Do
    Do <- will not be written
    Im
    Im <- will not be written
```

```
### [c] / [counter] ###
#### Example: [c] ####
    0
    1
    2
    3

#### Example: [c, 5] ####
    5
    6
    7
    8

#### Example: [c, 2, 2] ####
    2
    4
    6
    8

#### Example: [c, 6, 2, 2] ####
    06
    08
    10
    12
```

```
### [d] / [date] ###
#### Example: [d] ####
    2015-01-30
    2015-01-30 <- will not be written
    2015-01-30 <- will not be written
    2015-01-30 <- will not be written

#### Example: [d, d.MM.YYYY] ####
    30.01.2015
    30.01.2015 <- will not be written
    30.01.2015 <- will not be written
    30.01.2015 <- will not be written
```

```
### [s, X] / [size, X] ###
#### Example: [s, u] ####
    Text.txt
    Text2.txt
    image-01.jpg
    image-005.jpg

#### Example: [s, l] ####
    txt
    .txt
    -01.jpg
    -005.jpg

#### Example: [s, l][n, 1, 1][s, u][n, 2]####
    tEXT.TXT
    tEXT2.TXT
    iMAGE-01.JPG
    iMAGE-005.JPG
```

```
### [e] / [extension] ###
#### Example: [e] ####
    .txt
    .txt <- will not be written
    .jpg
    .jpg <- will not be written
```

## Examples 2 ##

You can also combine tags and text.

```
### Example: [n] - [d, d.MM.YYYY] ###
    Text.txt - 30.01.2015
    Text2.txt - 30.01.2015
    image-01.jpg - 30.01.2015
    image-005.jpg - 30.01.2015
```

```
### Example: [f] - [c, 1, 1, 2] ###
    myDocuments - 01
    myDocuments - 02
    myImages - 03
    myImages - 04
```

```
### Example: [s, u][f][s, l] - [c, 1, 1, 2] ###
    MYDOCUMENTS - 01
    MYDOCUMENTS - 02
    MYIMAGES - 03
    MYIMAGES - 04
```