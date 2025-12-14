import {createI18n} from 'vue-i18n'
import ru from './languages/ru.js'
import en from './languages/en.js'

const i18n = createI18n({
    locale: 'ru',
    legacy: false,
    fallbackLocale: 'en',
    messages: {
        ru,
        en
    },
});

export default i18n;