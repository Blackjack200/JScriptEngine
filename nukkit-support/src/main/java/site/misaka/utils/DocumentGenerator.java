package site.misaka.utils;

import site.misaka.script.object.CommandObject;
import site.misaka.script.object.ComplexObject;
import site.misaka.script.object.InternalObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public class DocumentGenerator {
    public static void main(String args[]) {
        Class clazz = CommandObject.class;
        StringBuilder builder = new StringBuilder();
        builder.append("## Field: `").append(clazz.getSimpleName()).append("`\n\n");
        builder.append(" Methods: \n");
        builder.append("```php\n");
        for (Method method : clazz.getDeclaredMethods()) {
            int mod = method.getModifiers();
            if (Modifier.isPublic(mod) && !Modifier.isAbstract(mod) && !Modifier.isStatic(mod)) {
                builder.append("  function ").append(method.getName()).append("(");
                builder.append(implode(", ", method.getParameters()));
                builder.append(") : ").append(method.getReturnType().getSimpleName()).append("\n");
            }
        }
        builder.append("```\n");
        System.out.println(builder.toString());
    }

    public static String paramToString(Parameter param) {
        final StringBuilder sb = new StringBuilder();
        final Type type = param.getParameterizedType();
        String typename = type.getTypeName();
        typename = typename.substring(typename.lastIndexOf(".") + 1);

        sb.append(Modifier.toString(param.getModifiers()));

        if (0 != param.getModifiers())
            sb.append(' ');

        if (param.isVarArgs())
            sb.append(typename.replaceFirst("\\[\\]$", "..."));
        else
            sb.append(typename);

        sb.append(' ');
        sb.append(param.getName());

        return sb.toString();
    }

    public static String implode(String glue, Object args[]) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = args.length - 1; i <= len; i++) {
            if (args[i] instanceof Parameter) {
                builder.append(paramToString((Parameter) args[i]));
            } else {
                builder.append(args[i].toString());
            }
            if (i != len) {
                builder.append(glue);
            }
        }
        return builder.toString();
    }
}
