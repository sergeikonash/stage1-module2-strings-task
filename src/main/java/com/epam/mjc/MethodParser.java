package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        MethodSignature methodSignature;
        signatureString = signatureString.replaceAll("\\)", "");
        List<String> substrings = Arrays.asList(signatureString.split("\\("));
        List<String> substringsWithMethodName = Arrays.asList(substrings.get(0).split(" "));

        signatureString = substrings.get(1).replaceAll(",", "");
        List<String> substringsWithArguments = Arrays.asList(signatureString.split(" "));
        if (substringsWithArguments.size() > 1) {
            List<MethodSignature.Argument> arguments = new ArrayList<>();
            for (int i = 0; i < substringsWithArguments.size(); i = i + 2) {
                arguments.add(new MethodSignature.Argument(substringsWithArguments.get(i), substringsWithArguments.get(i+1)));
            }
            if (substringsWithMethodName.size() == 2) {
                methodSignature = new MethodSignature(substringsWithMethodName.get(1), arguments);
                methodSignature.setReturnType(substringsWithMethodName.get(0));
            } else {
                methodSignature = new MethodSignature(substringsWithMethodName.get(2), arguments);
                methodSignature.setAccessModifier(substringsWithMethodName.get(0));
                methodSignature.setReturnType(substringsWithMethodName.get(1));
            }
        } else {
            if (substringsWithMethodName.size() == 2) {
                methodSignature = new MethodSignature(substringsWithMethodName.get(1));
            } else {
                methodSignature = new MethodSignature(substringsWithMethodName.get(2));
            }
        }
        return methodSignature;
    }
}
