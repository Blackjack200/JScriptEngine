package site.misaka.quercus;

import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.QuercusExitException;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.function.AbstractFunction;
import com.caucho.quercus.lib.*;
import com.caucho.quercus.lib.curl.CurlModule;
import com.caucho.quercus.lib.date.DateModule;
import com.caucho.quercus.lib.db.*;
import com.caucho.quercus.lib.dom.QuercusDOMModule;
import com.caucho.quercus.lib.file.FileModule;
import com.caucho.quercus.lib.filter.FilterModule;
import com.caucho.quercus.lib.gae.GaeUserServiceModule;
import com.caucho.quercus.lib.gettext.GettextModule;
import com.caucho.quercus.lib.i18n.MbstringModule;
import com.caucho.quercus.lib.i18n.UnicodeModule;
import com.caucho.quercus.lib.image.ImageModule;
import com.caucho.quercus.lib.jms.JMSModule;
import com.caucho.quercus.lib.json.JsonModule;
import com.caucho.quercus.lib.mcrypt.McryptModule;
import com.caucho.quercus.lib.pdf.PDFModule;
import com.caucho.quercus.lib.reflection.ReflectionModule;
import com.caucho.quercus.lib.regexp.RegexpModule;
import com.caucho.quercus.lib.simplexml.SimpleXMLModule;
import com.caucho.quercus.lib.spl.SplModule;
import com.caucho.quercus.lib.string.StringModule;
import com.caucho.quercus.lib.xml.XmlModule;
import com.caucho.quercus.lib.zip.ZipModule;
import com.caucho.quercus.lib.zlib.ZlibModule;
import com.caucho.quercus.module.AbstractQuercusModule;
import com.caucho.quercus.page.InterpretedPage;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.quercus.parser.QuercusParser;
import com.caucho.quercus.program.QuercusProgram;
import com.caucho.quercus.script.EncoderStream;
import com.caucho.quercus.script.QuercusScriptEngine;
import com.caucho.quercus.script.QuercusScriptEngineFactory;
import com.caucho.vfs.*;
import site.misaka.engine.JSR223Adapter;

import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;

public class QuercusAdapter extends JSR223Adapter<QuercusScriptEngine> {
    public QuercusAdapter(QuercusScriptEngine engine) {
        super(engine);
    }

    private Env env;

    @Override
    public boolean load(String code) {
        //ALL Module from Quercus
        this.engine.getQuercus().addInitModule(new ApacheModule());
        this.engine.getQuercus().addInitModule(new ApcModule());
        this.engine.getQuercus().addInitModule(new Array2Module());
        this.engine.getQuercus().addInitModule(new BcmathModule());
        this.engine.getQuercus().addInitModule(new ClassesModule());
        this.engine.getQuercus().addInitModule(new CoreModule());
        this.engine.getQuercus().addInitModule(new CtypeModule());
        this.engine.getQuercus().addInitModule(new ErrorModule());
        this.engine.getQuercus().addInitModule(new ExifModule());
        this.engine.getQuercus().addInitModule(new FunctionModule());
        this.engine.getQuercus().addInitModule(new HashModule());
        this.engine.getQuercus().addInitModule(new HtmlModule());
        this.engine.getQuercus().addInitModule(new JavaModule());
        this.engine.getQuercus().addInitModule(new MathModule());
        this.engine.getQuercus().addInitModule(new MhashModule());
        this.engine.getQuercus().addInitModule(new NetworkModule());
        this.engine.getQuercus().addInitModule(new OptionsModule());
        this.engine.getQuercus().addInitModule(new OutputModule());
        this.engine.getQuercus().addInitModule(new QuercusModule());
        this.engine.getQuercus().addInitModule(new TokenModule());
        this.engine.getQuercus().addInitModule(new VariableModule());
        //curl
        this.engine.getQuercus().addInitModule(new CurlModule());
        //date
        this.engine.getQuercus().addInitModule(new DateModule());
        //db
        this.engine.getQuercus().addInitModule(new MysqliModule());
        this.engine.getQuercus().addInitModule(new MysqlModule());
        this.engine.getQuercus().addInitModule(new OracleModule());
        this.engine.getQuercus().addInitModule(new PDOModule());
        this.engine.getQuercus().addInitModule(new PostgresModule());
        //dom
        this.engine.getQuercus().addInitModule(new QuercusDOMModule());
        //file
        this.engine.getQuercus().addInitModule(new FileModule());
        //filter
        this.engine.getQuercus().addInitModule(new FilterModule());
        //gae
        this.engine.getQuercus().addInitModule(new GaeUserServiceModule());
        //gettext
        this.engine.getQuercus().addInitModule(new GettextModule());
        //i18n
        this.engine.getQuercus().addInitModule(new MbstringModule());
        this.engine.getQuercus().addInitModule(new UnicodeModule());
        //image
        this.engine.getQuercus().addInitModule(new ImageModule());
        //jms
        this.engine.getQuercus().addInitModule(new JMSModule());
        //json
        this.engine.getQuercus().addInitModule(new JsonModule());
        //mail
        //this.engine.getQuercus().addInitModule(new MailModule());
        //mcrypt
        this.engine.getQuercus().addInitModule(new McryptModule());
        //memcached NOOP
        //pdf
        this.engine.getQuercus().addInitModule(new PDFModule());
        //reflection
        this.engine.getQuercus().addInitModule(new ReflectionModule());
        //regexp
        this.engine.getQuercus().addInitModule(new RegexpModule());
        //session
        //this.engine.getQuercus().addInitModule(new SessionModule());
        //simplexml
        this.engine.getQuercus().addInitModule(new SimpleXMLModule());
        //spl
        this.engine.getQuercus().addInitModule(new SplModule());
        //string
        this.engine.getQuercus().addInitModule(new StringModule());
        //xml
        this.engine.getQuercus().addInitModule(new XmlModule());
        //zip
        this.engine.getQuercus().addInitModule(new ZipModule());
        //zlib
        this.engine.getQuercus().addInitModule(new ZlibModule());
        this.engine.getQuercus().addInitModule(new UrlModule());

        this.engine.getQuercus().getModuleContext().addModule("JSE_Extern", new AbstractQuercusModule() {
            public Object extern(Env env,
                                 String className) {
                Object object = env.wrapJava(JavaModule.java_class(env, className));
                engine.put(className.substring(className.lastIndexOf(".")), object);
                return object;
            }

            public void extern_name(Env env, String className, String exportName) {
                engine.put(exportName, extern(env, className));
            }
        });

        this.engine.getQuercus().init();
        QuercusContext quercus = this.engine.getQuercus();
        StringReader script = new StringReader(code);

        try {
            QuercusProgram program;

            if (this.engine.isUnicodeSemantics()) {
                program = QuercusParser.parse(quercus, null, script);
            } else {
                InputStream is
                        = EncoderStream.open(script, quercus.getScriptEncoding());

                ReadStream rs = new ReadStream(new VfsStream(is, null));

                program = QuercusParser.parse(quercus, null, rs);
            }

            Writer writer = this.engine.getContext().getWriter();

            WriteStream out;

            if (writer != null) {
                WriterStreamImpl s = new WriterStreamImpl();
                s.setWriter(writer);
                WriteStream os = new QuercusWriteStream(s);

                os.setNewlineString("\n");

                String outputEncoding = quercus.getOutputEncoding();

                if (outputEncoding == null) {
                    outputEncoding = "utf-8";
                }

                try {
                    os.setEncoding(outputEncoding);
                } catch (Exception ignored) {
                }

                out = os;
            } else {
                out = new NullWriteStream();
            }

            QuercusPage page = new InterpretedPage(program);

            if (env == null) {
                env = new Env(quercus, page, out, null, null);
            }

            env.setScriptContext(this.engine.getContext());
            env.start();
            try {
                program.execute(env);
            } catch (QuercusExitException ignored) {
            }

            out.flushBuffer();
            out.free();
            if (writer != null) {
                writer.flush();
            }
        } catch (Throwable ignored) {
            return false;
        }
        return true;
    }

    @Override
    public void invoke(String name, Object... args) {
        try {
            AbstractFunction function = env.getFunction(StringValue.create(name).toString(env));
            if (function != null) {
                Value values[] = new Value[args.length];
                for (int i = 0; i < args.length; i++) {
                    values[i] = env.wrapJava(args[i]);
                }
                function.call(env, values);
            }
        } catch (Throwable ignored) {

        }
    }

    public static void main(String args[]) {
        QuercusProcessor processor = new QuercusProcessor(new QuercusScriptEngineFactory());
        QuercusAdapter adapter = processor.process("<?php function test2($e){extern('java.lang.System')::out->print(\"demo\");}", new HashMap<>());
        adapter.invoke("test2", System.out);
    }
}
