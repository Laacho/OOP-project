package bg.tu_varna.sit.a4.f22621679.parser;

import bg.tu_varna.sit.a4.f22621679.modules.XML;

/***
 * An interface defining functionalities for parsing XML documents.
 * Parses an XML file from the specified file name and returns an in-memory representation of the XML document.
 */
public interface Parser {
     XML parse(String fileName);
}
