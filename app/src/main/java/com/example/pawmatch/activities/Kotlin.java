//class VaccinationAdapter(private val vaccinationList: List<Vaccination>) : RecyclerView.Adapter<VaccinationAdapter.ViewHolder>() {
//
//class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val nameTextView: TextView = itemView.findViewById(R.id.vaccinationName)
//    val statusTextView: TextView = itemView.findViewById(R.id.vaccinationStatus)
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vaccination, parent, false)
//    return ViewHolder(view)
//}
//
//override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//    val vaccination = vaccinationList[position]
//    holder.nameTextView.text = vaccination.name
//    holder.statusTextView.text = vaccination.status
//}
//
//override fun getItemCount(): Int {
//    return vaccinationList.size
//}
//}
