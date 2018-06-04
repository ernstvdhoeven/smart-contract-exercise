package src

data class LegalDocuments(val documentHashes: List<String>)
{
    /**
     * Returns true if legal documents are in the current state that were not in the previous state.
     */
    fun legalDocumentsHaveBeenAdded(oldLegalDocuments: LegalDocuments) : Boolean
    {
        return documentHashes != oldLegalDocuments.documentHashes
    }

    /**
     * Returns true if the formatting of all the legal document hashes is correct.
     */
    fun checkNewDocumentsFormatting() : Boolean
    {
        return documentHashes.mapNotNull { x -> x.toIntOrNull() }.size == documentHashes.size
    }

    /**
     * Returns true if a document hash matches one of the signatures.
     */
    fun checkIfDocumentHashMatchesSignature(signatures: Signatures) : Boolean
    {
        for (signature in signatures.signatures)
            if (documentHashes.contains(signature))
                return true

        return false
    }
}