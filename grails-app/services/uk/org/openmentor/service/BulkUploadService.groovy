package uk.org.openmentor.service

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.*;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.Node;

class BulkUploadService {

	// Called to handle a single zip entry. 
	// if this is a zip file, we can recurse, basically. 
	private void uploadZipEntry(ZipEntry ze, InputStream data) {
		String name = ze.getName()
		if(name =~ /\.zip/)	{
			upload(data)
		} else {
			System.err.println("File: " + name)			
		}
	}

	void upload(File fileZip) {
		ZipFile zipFile = new ZipFile(fileZip);
		Enumeration entries = zipFile.getEntries();

		while (entries.hasMoreElements()) {
            ZipArchiveEntry zipEntry = entries.nextElement();
            handleUploadEntry(zipFile, zipEntry);
        }
    }

    class ZipArchiveEntryComparator implements Comparator<ZipArchiveEntry> {
	    @Override
	    public int compare(ZipArchiveEntry a, ZipArchiveEntry b) {
	    	return b.getTime() - a.getTime();
	    }
	}

    void handleUploadZipFile(File entryFile) {
 		ZipFile zipFile = new ZipFile(entryFile);
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		List<ZipArchiveEntry> contentEntries = new ArrayList<ZipArchiveEntry>()
		ZipArchiveEntry dataEntry = null

		while (entries.hasMoreElements()) {
            ZipArchiveEntry zipEntry = entries.nextElement();
            String name = zipEntry.getName()
            if (name =~ /^Assessment Summary.*\(pt3\)\.htm$/) {
            	dataEntry = zipEntry
            } else if (name == "PT3OULogo.gif") {
            	// Do nothing
            } else {
            	contentEntries.add(zipEntry)
            }
		}

		if (! dataEntry) {
			return;
		}

		// If there are several entries, pick the newest

		if (contentEntries.size() > 1) {
			Collections.sort(contentEntries, new ZipArchiveEntryComparator());
		}
		handleUploadSubmission(zipFile, dataEntry, contentEntries.get(0))
		zipFile.close()
    }

    void handleUploadSubmission(ZipFile zipFile, ZipArchiveEntry dataEntry, ZipArchiveEntry contentEntry) {

		InputStream dataStream = zipFile.getInputStream(dataEntry);
		byte[] dataBytes = IOUtils.toByteArray(dataStream);
		String dataBody = new String(dataBytes);

		Parser parser = new Parser(new Lexer(dataBody));
		NodeList nl = parser.parse(null);
		NodeList cells = nl.extractAllNodesThatMatch(new TagNameFilter("td"), true);

		List<String> values = new ArrayList<String>();

		SimpleNodeIterator nodeIterator = cells.elements();
	    while (nodeIterator.hasMoreNodes()) {
	    	Node node = nodeIterator.nextNode();
			TableColumn tag = (TableColumn)node;
			values.add(tag.toPlainTextString())
		}
		String name = values[7];
		String studentIdentifier = values[15];
		String course = values[19];
		String assignment = values[21];
		String tutorIdentifier = values[26];
		String mark = values[53];
		log.error([name, studentIdentifier, course, assignment, tutorIdentifier, mark]);    
	}


    // If we have multiple content files, choose the most recent. 

    void handleUploadEntry(ZipFile zipFile, ZipArchiveEntry zipEntry) {
    	if (zipEntry.isDirectory()) {
            return;
        }

        String name = zipEntry.getName()
        if (name =~ /\.zip/)	{
	        handleUploadZipEntry(zipFile, zipEntry);
        }
    }

    void handleUploadZipEntry(ZipFile zipFile, ZipArchiveEntry zipEntry) {
        File temp = File.createTempFile("temp",".zip")
        temp.deleteOnExit()
        InputStream inStream = zipFile.getInputStream(zipEntry)
        OutputStream outStream = new FileOutputStream(temp)
        IOUtils.copy(inStream, outStream)
        outStream.close()
        handleUploadZipFile(temp)
    }
}