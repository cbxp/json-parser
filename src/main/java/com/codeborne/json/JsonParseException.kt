package com.codeborne.json

import java.text.ParseException

class JsonParseException(message: String?, errorOffset: Int) : ParseException(message, errorOffset)
