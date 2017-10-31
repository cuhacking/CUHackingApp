class CreateCompanies < ActiveRecord::Migration[5.1]
  def change
    create_table :companies do |t|
      t.belongs_to :event

      t.string :name
      t.string :tier
      t.string :website_url
      t.string :logo

      t.timestamps
    end
  end
end
