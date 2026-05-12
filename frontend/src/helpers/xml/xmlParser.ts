export function parseXmlComment(xmlString) {
    const parser = new DOMParser()
    const xmlDoc = parser.parseFromString(xmlString, 'application/xml')
    const sections = {}

    xmlDoc.documentElement.childNodes.forEach(section => {
        if (section.nodeType === 1) {
            const sectionName = section.nodeName
            sections[sectionName] = {}

            section.childNodes.forEach(field => {
                if (field.nodeType === 1) {
                    sections[sectionName][field.nodeName] = field.textContent
                }
            })
        }
    })

    return sections
}

function parseTranslationsXML(xmlString: string): { lang: string; text: string }[] {
    if (!xmlString || xmlString === '""' || xmlString === 'null') {
        return [];
    }

    let cleanXml = xmlString;
    if (cleanXml.startsWith('"') && cleanXml.endsWith('"')) {
        try {
            cleanXml = JSON.parse(cleanXml);
        } catch (e) {
            cleanXml = cleanXml.substring(1, cleanXml.length - 1).replace(/\\"/g, '"');
        }
    }
    if (!cleanXml || cleanXml === '""') return [];
    const parser = new DOMParser()
    const xmlDoc = parser.parseFromString(cleanXml, 'application/xml')
    const translations: { lang: string; text: string }[] = [];

    xmlDoc.querySelectorAll('translation').forEach(node => {
        const lang = node.getAttribute('lang')
        const text = node.textContent.trim() || ""
        if (lang) {
            translations.push({ lang, text });
        }
    })

    return translations
}

export default parseTranslationsXML
export function buildTranslationsXML(translations: { lang: string; text: string }[]) {
    const xmlDoc = document.implementation.createDocument('', '', null);
    const root = xmlDoc.createElement('translations');

    translations.forEach(t => {
        const node = xmlDoc.createElement('translation');
        node.setAttribute('lang', t.lang);
        node.textContent = t.text.replace(/\n/g, ' ');
        root.appendChild(node);
    });

    xmlDoc.appendChild(root);
    return new XMLSerializer().serializeToString(xmlDoc);
}
