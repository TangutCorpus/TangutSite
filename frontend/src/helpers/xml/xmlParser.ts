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

export function parseTranslationsXML(xmlString: string): { lang: string; text: string }[] {
    if (!xmlString) return [];
    const parser = new DOMParser()
    const xmlDoc = parser.parseFromString(xmlString, 'application/xml')
    const translations: { lang: string; text: string }[] = [];

    xmlDoc.querySelectorAll('translation').forEach(node => {
        const lang = node.getAttribute('lang')
        const text = node.textContent.trim() || ""
        if (lang && text) {
            translations.push({ lang, text });
        }
    })

    return translations
}
export function buildTranslationsXML(translations: { lang: string; text: string }[]) {
    const xmlDoc = document.implementation.createDocument('', '', null);
    const root = xmlDoc.createElement('translations');

    translations.forEach(t => {
        const node = xmlDoc.createElement('translation');
        node.setAttribute('lang', t.lang);
        node.textContent = t.text;
        root.appendChild(node);
    });

    xmlDoc.appendChild(root);
    return new XMLSerializer().serializeToString(xmlDoc);
}
