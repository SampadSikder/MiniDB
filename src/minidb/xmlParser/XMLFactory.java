import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XMLFactory{
    protected Document doc;
    public abstract XMLFiles getFile();
    public abstract void createFile();

}

class RegistryFileFactory extends XMLFactory{
    @Override
    public XMLFiles getFile() {
        return new RegistryFile();
    }
    public void createFile(RegistryFile registryFile) {
        Element rootElem = doc.createElement("Xroot");
        Element meta = doc.createElement(TAG_META);
        Element data = doc.createElement(TAG_STORAGE);

        rootElem.appendChild(meta);
        rootElem.appendChild(data);
        doc.appendChild(rootElem);

        registryFile.updateFile();
    }
}

class DatabaseFileFactory extends XMLFactory{
    @Override
    public XMLFiles getFile() {
        return new DatabaseFile();
    }
    public void createFile(DatabaseFile databaseFile) {
        Element rootElem = doc.createElement("root");
        Element emptyDb = databaseFile.getaddDbEntry("empty", "true");

        rootElem.appendChild(emptyDb);
        doc.appendChild(rootElem);

        getFile(databaseFile.getDatabasePath("empty", true));
        databaseFile.updateFile();

        System.out.println("Intialized: " + xmlFile.getPath());
    }
}
