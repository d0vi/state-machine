/**
 * On this package you can find the model entities and value objects.
 * <p/>
 * In his excellent book <em>Domain-Driven Design</em>, Eric Evans creates a classification of the different kinds of
 * domain objects that developers are likely to run into.
 * <ul>
 *     <li>Entity: objects that have a distinct identity that runs through time and different representations.</li>
 *     <li>Value Object: objects that matter only as the combination of their attributes. Two value objects with the
 *     same values for all their attributes are considered equal.</li>
 *     <li>Service: a standalone operation within the context of the domain. A service object collects one or more
 *     services into an object. Typically there will be only one instance of each service object type within the
 *     execution context.</li>
 * </ul>
 *
 * @author Jesús Iglesias
 */
package com.imatia.statemachine.domain;