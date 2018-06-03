package src

data class LegalDocuments(val documentHashes: List<String>)
{
    fun legalDocumentsHaveBeenAdded(oldLegalDocuments: LegalDocuments) : Boolean
    {
        return documentHashes != oldLegalDocuments.documentHashes
    }

    fun checkNewDocumentsFormatting() : Boolean
    {
        return documentHashes.mapNotNull { x -> x.toIntOrNull() }.size == documentHashes.size
    }
}