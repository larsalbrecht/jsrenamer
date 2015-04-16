"Greys.Anatomy.S11E01.Im.Wind.verloren.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"
"Greys.Anatomy.S11E02.Das.fehlende.Puzzleteil.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"
"Greys.Anatomy.S11E03.Irrtum.ausgeschlossen.GERMAN.DUBBED.DL.720p.WebHD.h264-euHD"

testList = array
pattern = "[SH] - [SI] - [S][D]" // [D] will be overwritten with 
substringSeparator = "."

// separate by substringSeparator:
Greys | Anatomy | S11E01 | Im | Wind | verloren | GERMAN | DUBBED | DL | 720p | WebHD | h264-euHD
Greys | Anatomy | S11E02 | Das | fehlende | Puzzleteil | GERMAN | DUBBED | DL | 720p | WebHD | h264-euHD
Greys | Anatomy | S11E02 | Irrtum | ausgeschlossen | GERMAN | DUBBED | DL | 720p | WebHD | h264-euHD

// find longest
Greys | Anatomy | S11E02 | Das | fehlende | Puzzleteil | GERMAN | DUBBED | DL | 720p | WebHD | h264-euHD

// compare
foreach(
i = 0
	Greys -> Greys = Greys
	Anatomy -> Anatomy = Anatomy
	S11E01 -> S11E02 = S11E0*
	Im -> Das = *
	Wind -> fehlende = *
	verloren -> Puzzleteil = *
	GERMAN -> GERMAN = GERMAN
	....
	
i = 1
	Greys -> Greys = Greys
	Anatomy -> Anatomy = Anatomy
	S11E01 -> S11E03 = S11E0*
	Im -> Irrtum = *
	Wind -> ausgeschlossen = *
	verloren -> GERMAN = *
	GERMAN -> DUBBED = *
	....
)

// results
1)
Greys | Anatomy | S11E0* | * | * | * | GERMAN | DUBBED | DL | 720p | WebHD | h264-euHD

2)
Greys | Anatomy | S11E0* | * | * | * | * | * | * | * | * | *

// compare backwards
foreach(
i = 0
	h264-euHD -> h264-euHD = h264-euHD
	WebHD -> WebHD = WebHD
	720p -> 720p = 720p
	DL -> DL = DL
	DUBBED -> DUBBED = DUBBED
	GERMAN -> GERMAN = GERMAN
	verloren -> Puzzleteil = *
	Wind -> fehlende = *
	Im -> Das = *
	S11E01 -> S11E02 = S11E0*
	Anatomy -> Anatomy = Anatomy
	Greys -> Greys = Greys
i = 1
	h264-euHD -> h264-euHD = h264-euHD
	WebHD -> WebHD = WebHD
	720p -> 720p = 720p
	DL -> DL = DL
	DUBBED -> DUBBED = DUBBED
	GERMAN -> GERMAN = GERMAN
	verloren -> ausgeschlossen = *
	Wind -> Irrtum = *
	Im -> S11E02 = *
	S11E01 -> Anatomy = *
	Anatomy -> Greys = *
	Greys -> - = [ONE TO MUCH]
)

// results
1)

h264-euHD | WebHD | 720p | DL | DUBBED | GERMAN | * | * | * | S11E0* | Anatomy | Greys

2)
h264-euHD | WebHD | 720p | DL | DUBBED | GERMAN | * | * | * | * | * | *


// define strings
forward Hard String:
0, 1

backward Hard String:
0, 5