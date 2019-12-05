package lt.liutikas.dropbox.validation;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class ValidationProcessor implements Processor {

    private List<String> requiredProperties;
    private List<String> givenProperties;
    private List<List<String>> givenPersonsValues;

    @Override
    public void process(Exchange exchange) {
        List<List<String>> data = exchange.getIn().getBody(List.class);

        boolean valid;

        if (data.isEmpty()) {
            valid = false;
        } else {
            extractFields(data);
            valid = isDataValid();
        }

        exchange.getIn().setHeader("valid", valid);
    }

    private void extractFields(List<List<String>> data) {
        requiredProperties = getRequiredProperties();
        givenProperties = getGivenProperties(data);
        givenPersonsValues = getGivenPersonsValues(data);
    }

    private LinkedList<String> getRequiredProperties() {
        return new LinkedList<>(Arrays.asList("name", "officialId", "languageId"));
    }

    private List<String> getGivenProperties(List<List<String>> data) {
        return data.get(0);
    }

    private List<List<String>> getGivenPersonsValues(List<List<String>> data) {
        return data.subList(1, data.size());
    }

    private boolean isDataValid() {
        if (hasCorrectProperties()) {
            return hasValidValues();
        }
        return false;
    }

    private boolean hasNoRows() {
        return givenPersonsValues.isEmpty();
    }

    private boolean hasValidValues() {
        if (hasNoRows()) {
            return false;
        }
        for (int i = 0; i < givenPersonsValues.size(); i++) {
            if (isRowInvalid(givenPersonsValues.get(i))) {
                return false;
            }
        }
        return true;


    }

    private boolean isRowInvalid(List<String> row) {
        return hasIncorrectValuesCount(row) || hasIncorrectDataType(row);
    }

    private boolean hasIncorrectValuesCount(List<String> row) {
        return row.size() != givenProperties.size();
    }

    private boolean hasIncorrectDataType(List<String> row) {
        for (int j = 0; j < givenProperties.size(); j++) {
            if (isDataTypeCorrect(givenProperties.get(j), row.get(j))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDataTypeCorrect(String property, String value) {
        switch (property) {
            case "name":
                if (containsNotJustLetters(value)) {
                    return true;
                }
                break;
            case "officialId":
            case "languageId":
                try {
                    Long.parseLong(value);
                } catch (NumberFormatException e) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean containsNotJustLetters(String element) {
        return !element.matches("[a-zA-Z]+");
    }

    private boolean hasCorrectProperties() {
        if (requiredProperties.size() != givenProperties.size()) {
            return false;
        }

        List<String> incorrectColumns = new LinkedList<>(givenProperties);
        incorrectColumns.removeAll(requiredProperties);
        return incorrectColumns.isEmpty();
    }

}
