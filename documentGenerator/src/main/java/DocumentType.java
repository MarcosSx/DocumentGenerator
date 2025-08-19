public enum DocumentType {
    CPF,
    CNPJ,
    CNPJ_ALPHA,
    RAIZ_CNPJ;

    public static DocumentType fromString(String type) {
        return DocumentType.valueOf(type.toUpperCase());
    }
}
