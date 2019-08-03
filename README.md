# Simple Index Search App  
  
## Code language  
Java 11  
  
## Creating a distribution  

```
./gradlew clean build
```

`search-1.0-SNAPSHOT.zip` file will be created under `build/distributions`.  
  
## Running the application  
Unzip the distribution file.   In the root directory of the unzip result run:   
  
```
./bin/search
```

## Usage
### show-fields
List searchable fields

```
    NAME
    	show-fields - Display searchable fields for searchable entities
    
    SYNOPSYS
    	show-fields
```

Eg.
```
shell:>show-fields
```

### search
Performs a full word match on field values of entities. 
Entity name can be Organization, User or Ticket (case insensitive).
The available fields for entities can be discovered by running `show-fields`.

```
    NAME
	search - Full word match on field values of Organizations, Users or Tickets
	
    SYNOPSYS
    	search [--entity] string  [--field] string  [--word] string  
    
    OPTIONS
    	--entity  string
    		Entity name. It can be [Organization | User | Ticket]
    		[Mandatory]
    
    	--field  string
    		Field to search. For field info see show-fields command.
    		[Mandatory]
    
    	--word  string
    		Value to search (full work match only)
    		[Mandatory]
```

Eg.
```
shell:>search organization details MegaCorp
```

## Help
For "entire" app help
```
shell:>help
```

For specific command help
``` 
shell:> help <command>
```

## Assumptions and Trade-offs

TBW