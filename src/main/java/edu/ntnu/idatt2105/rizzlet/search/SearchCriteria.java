package edu.ntnu.idatt2105.rizzlet.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a search criteria used for filtering entities.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    /**
     * The key or field to filter on.
     */
    private String key;

    /**
     * The operation to perform, such as equals, contains, etc.
     */
    private String operation;

    /**
     * The value to filter on.
     */
    private Object value;

}
