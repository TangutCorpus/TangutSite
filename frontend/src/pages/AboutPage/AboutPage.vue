<template>
  <div class="container mx-auto p-6">
    <SectionWrapper id="features" title="Features">
      <FeatureList :features="features" />
    </SectionWrapper>

    <SectionWrapper id="team" title="Our Team">
      <TeamList :members="team" />
    </SectionWrapper>

    <SectionWrapper id="pricing" title="Pricing">
      <PricingTable :headers="pricingHeaders" :rows="pricingRows" />
    </SectionWrapper>

    <SectionWrapper id="contact" title="Contact">
      <ContactForm />
    </SectionWrapper>

    <SectionWrapper id="faq" title="FAQ">
      <FAQList :faqs="faqs" />
    </SectionWrapper>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import FeatureList from '@/pages/AboutPage/components/FeatureList.vue';
import TeamList from '@/pages/AboutPage/components/TeamList.vue';
import PricingTable from '@/pages/AboutPage/components/PricingTable.vue';
import ContactForm from '@/pages/AboutPage/components/ContactForm.vue';
import FAQList from '@/pages/AboutPage/components/FAQList.vue';
import SectionWrapper from '@/pages/AboutPage/components/SectionWrapper.vue';
import notFoundImage from '@/assets/images/404.svg'

const features = ref([{ title: "Lorem Ipsum", description: "Lorem Ipsum" }]);
const team = ref([
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: notFoundImage },
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: notFoundImage },
  { name: "Lorem Ipsum", role: "Lorem Ipsum", image: notFoundImage }]
);
const pricingHeaders = ref(["Lorem Ipsum"]);
const pricingRows = ref([{ id: 1, values: ["Lorem Ipsum"] }]);
const faqs = ref([{ question: "Lorem Ipsum?", answer: "Lorem Ipsum" }]);
</script>