/* =======================================================================
 * Copyright 2004-2012 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */


/*
 * Rewritten to use POI scratchpad directly. This works well for .doc files, but not
 * for .docx. A different extractor is required for that. 
 */

package uk.org.openmentor.extractor.impl;

import uk.org.openmentor.extractor.Extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.hwpf.extractor.WordExtractor;

public class ExtractStandard implements Extractor {
	
	
	private WordExtractor extractor;
	private Set<String> comments = new HashSet<String>();
	private String body;
	
    /*
     * Annotations in a few cases may contain fields. Actually, so may all the
     * text, which is irritating. So we need a way of skipping out all the
     * field stuff, and for this, regular expressions are a good solution.
     * Essentially, we should look for ASCII 19, which starts a field, and
     * ASCII 21, which ends the field. Delete these, and all other text, except
     * stuff which follows ASCII 20, which is the text view.
     */
    private static Log log = LogFactory.getLog(ExtractStandard.class);

    public ExtractStandard() {
    	
    }

    /**
     * Constructs a Word document from the input. Parses the document and places
     * all the important stuff into data structures.
     *
     * @param stream The stream representing the original file
     * @throws IOException if there is a problem while parsing the document.
     */
    public synchronized void extract(InputStream stream) throws IOException {
        extractStream(stream);
    }
    
    /*
     * Most of this code is broken - in a complex file, you can't assume that
     * the file offset can be calculated in this way. Instead, we should not use
     * fcMin, but use the piece table to calculate the offsetting. This is best
     * done once and cached.
     */

    private void extractStream(InputStream inputStream) throws IOException {
    	extractor = new WordExtractor(inputStream);
    	inputStream.close();
    	
    	String[] commentsArray = extractor.getCommentsText();
    	comments.clear();
    	comments.addAll(Arrays.asList(commentsArray));
    	body = extractor.getText();
    }

    public String getBody() {
        return body;
    }

    public Set getAnnotations() {
    	return comments;
    }
}

