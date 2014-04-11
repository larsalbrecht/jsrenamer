STEP1: Find all same and different parts
STEP2: Find the separator(one of: " " | ".") and split
STEP3: Own filter for different implementations

ORIG:	The.Vampire.Diaries.S04E01.STAB.720p.HDTV.X264-DIMENSION.mkv
STEP1:	The.Vampire.Diaries.S04E | 01 | . | STAB | . | 720p.HDTV.X264-DIMENSION.mkv
STEP2:	The | Vampire | Diaries | S04E | 01 | STAB | 720p | HDTV | X264-DIMENSION.mkv

ORIG:	The.Vampire.Diaries.S04E02.ESTA.720p.HDTV.X264-DIMENSION.mkv
STEP1:	The.Vampire.Diaries.S04E | 02 | . | ESTA | . | 720p.HDTV.X264-DIMENSION.mkv
STEP2:	The | Vampire | Diaries | S04E | 02 | ESTA | 720p | HDTV | X264-DIMENSION.mkv

ORIG:	The.Vampire.Diaries.S04E12.BLUB.BLAT.720p.HDTV.X264-DIMENSION.mkv
STEP1:	The.Vampire.Diaries.S04E | 12 | . | BLUB.BLAT | . | 720p.HDTV.X264-DIMENSION.mkv
STEP2:	The | Vampire | Diaries | S04E | 12 | BLUB | BLAT | 720p | HDTV | X264-DIMENSION.mkv


FOR SERIES:
pattern: "[STATIC] - [S[0-9]{0,2}?][E[0-9]{0,2}?] - [VAR]"
separator: " "
nonmatches: IGNORE

STEP3:	The Vampire Diaries - S04E01 - STAB.mkv

STEP3:	The Vampire Diaries - S04E02 - ESTA.mkv

STEP3:	The Vampire Diaries - S04E12 - BLUB BLAT.mkv


FOR STEP1:
Map<position, string> staticStrings
0 The.Vampire.Diaries.S04E
2 .
4 .
5 720p.HDTV.X264-DIMENSION.mkv

Map<File, Map<position, string>> variableStrings
File A 
	1 : 01
	3 : STAB
	
File B:
	1 : 02
	3 : ESTA

File C:
	1 : 12
	3 : BLUB.BLAT
	
