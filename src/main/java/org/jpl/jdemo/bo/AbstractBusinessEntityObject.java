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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.DateTime;


/**
 * Abstract object
 */
@Entity
public abstract class AbstractBusinessEntityObject extends AbstractBusinessObject implements BusinessEntityObject {
	/** A generated serialVersionUID. */
	private static final long serialVersionUID = 8965768878008596700L;
	/** the technical identification. */
	@Id
	@Column(name = "id", nullable = false)
	private final UUID id; // NOPMD by limpens on 23-3-08 11:54
	/** the creation date. */
	@Column(name = "creationDate", nullable = false)
	private DateTime creationDate;
	/** the business identification. */
	@Column(name = "identity", nullable = false)
	private Serializable identity;
	/** is the object deleted. */
	@Column(name = "deleted", nullable = false)
	private boolean deleted;
	/** the last modified date. */
	@Column(name = "lastModifiedDate", nullable = false)
	private DateTime lastModifiedDate;

	/**
	 * Default constructor
	 */
	public AbstractBusinessEntityObject() { // NOPMD by limpens on 23-3-08 11:54
		super();
		id = UUID.randomUUID();
	}
	
	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#getLastModifiedDate()
	 * {@inheritDoc}
	 */
	@Override
	public final DateTime getLastModifiedDate() { // NOPMD by limpens on 23-3-08 11:51
		return lastModifiedDate;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#setLastModifiedDate(java.util.Date)
	 * {@inheritDoc}
	 */
	@Override
	public final void setLastModifiedDate(final DateTime date) {
		lastModifiedDate = date;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessEntityObject#getId()
	 * {@inheritDoc}
	 */
	@Override
	public final UUID getId() { // NOPMD by limpens on 23-3-08 11:53
		return id;
	}
	
	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#getCreationDate()
	 * {@inheritDoc}
	 */
	@Override
	public final DateTime getCreationDate() { // NOPMD by limpens on 23-3-08 11:53
		return creationDate;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessEntityObject#getIdentity()
	 * {@inheritDoc}
	 */
	@Override
	public final Serializable getIdentity() { // NOPMD by limpens on 23-3-08 11:53
		return identity;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#isDeleted()
	 * {@inheritDoc}
	 */
	@Override
	public final boolean isDeleted() { // NOPMD by limpens on 23-3-08 11:53
		return deleted;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#setCreationDate(java.util.Date)
	 * {@inheritDoc}
	 */
	@Override
	public final void setCreationDate(final DateTime date) {
		 creationDate = date;
	}

	/**
	 * @see org.jpl.jdemo.bo.BusinessObject#setDeleted(java.lang.Boolean)
	 * {@inheritDoc}
	 */
	@Override
	public final void setDeleted(final boolean isDeleted) {
		deleted = isDeleted;
	}
	
	/**
	 * @see org.jpl.jdemo.bo.BusinessEntityObject#setIdentity(java.io.Serializable)
	 * {@inheritDoc}
	 */
	@Override
	public final void setIdentity(final Serializable anIdentity) {
		this.identity = anIdentity;
	}
}
