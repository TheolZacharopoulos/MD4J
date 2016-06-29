package nl.cwi.examples.ccconcerns.patterns.simplelogging;

import java.util.Arrays;
import java.util.List;

import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

public class LoggingDataManager extends BasicDataManager {
    private List<String> fieldsToLog;
    
	public LoggingDataManager(String... fieldsToLog) {
		super();
		this.fieldsToLog = Arrays.asList(fieldsToLog);
	}
	
	@Override
	public MObject createManagedObject(Klass klass, Object... _inits) {
        return new LoggingMObject(fieldsToLog, klass, _inits);
    }
}