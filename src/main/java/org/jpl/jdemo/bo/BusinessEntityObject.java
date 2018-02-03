/*
 * Copyright (c) 2018 JP-L, https://www.jp-l.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.jpl.jdemo.bo;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;


/**
 * Business Entity Object
 */
public interface BusinessEntityObject extends BusinessObject, Serializable {
	/**
	 * Return the technical identification
	 * as stored as primary key in the database.
	 * 
	 * for RDBMS this maps as a string.
	 * 
	 * @return UUID
	 */
	UUID getId();
	
	/**
     * Return the identification as used as business key.
     * @return Serializable
     */
    Serializable getIdentity();

    /**
     * Set the identification as used as business key.
     * @param anIdentity an identity.
     */
    void setIdentity(Serializable anIdentity);
	/**
	 * Return a creation date.
	 * @return Date
	 */
	DateTime getCreationDate();

	/**
	 * Set the creation date.
	 * @param aDate the creation date.
	 */
    void setCreationDate(DateTime aDate);

    /**
     * Get the last modified date.
     * @return Date
     */
    DateTime getLastModifiedDate();

    /**
     * Set the last modified date.
     * @param aDate the last modified date
     */
    void setLastModifiedDate(DateTime aDate);
    
    /**
     * Return true if the business object is deleted.
     * @return Boolean
     */
    boolean isDeleted();

    /**
     * Flag deleted.
     * @param isDeleted true or false.
     */
    void setDeleted(boolean isDeleted);
    
}
